package com.java.quiz.questionservice.service;

import com.java.quiz.questionservice.entity.Question;
import com.java.quiz.questionservice.exception.InvalidDataException;
import com.java.quiz.questionservice.model.QuestionWrapper;
import com.java.quiz.questionservice.model.Response;
import com.java.quiz.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {

    QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public ResponseEntity<?> getQuestionsByCategory(String category){
        List<String> validCategories = List.of("Core Java", "Advanced Java");
        Map<String,String> response = new HashMap<>();
        response.put("Error","Invalid Category! Valid Categories are: "+ validCategories);

        if(!validCategories.contains(category)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(questionRepository.findByCategory(category));
    }

    public ResponseEntity<Map<String,String>> addQuestion(Question question) {
        questionRepository.save(question);
        Map<String,String> response = new HashMap<>();
        response.put("message", "Question added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Map<Integer, String>> updateQuestion(Integer id, Question question) {
        Optional<Question> optionalId = questionRepository.findById(id);
        if(optionalId.isPresent()){
            Question dbQuestion = optionalId.get();
            dbQuestion.setCategory(question.getCategory());
            dbQuestion.setDifficultyLevel(question.getDifficultyLevel());
            dbQuestion.setJavaQuestion(question.getJavaQuestion());
            dbQuestion.setOption1(question.getOption1());
            dbQuestion.setOption2(question.getOption2());
            dbQuestion.setOption3(question.getOption3());
            dbQuestion.setOption4(question.getOption4());
            dbQuestion.setRightAnswer(question.getRightAnswer());
            questionRepository.save(dbQuestion);
        }
        else{
            throw new InvalidDataException("Question not found");
        }
        Map<Integer,String> response = new HashMap<>();
        response.put(id,"Question updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Map<Integer, String>> deleteQuestion(Integer id) {
        Optional<Question> optionalId = questionRepository.findById(id);
        if(optionalId.isPresent()){
            questionRepository.deleteById(id);
        }
        else{
            throw new InvalidDataException("Id not found");
        }
        Map<Integer,String> response = new HashMap<>();
        response.put(id,"Question deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String catagoryName, Integer numQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(catagoryName,numQuestions);
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer questionId : questionIds){
            questions.add(questionRepository.findById(questionId).get());
        }
        for(Question question : questions){
            QuestionWrapper qw = new QuestionWrapper();
            qw.setId(question.getId());
            qw.setJavaQuestion(question.getJavaQuestion());
            qw.setOption1(question.getOption1());
            qw.setOption2(question.getOption2());
            qw.setOption3(question.getOption3());
            qw.setOption4(question.getOption4());
            questionWrappers.add(qw);
        }
        return ResponseEntity.status(HttpStatus.OK).body(questionWrappers);
    }


    public ResponseEntity<String> getScore(List<Response> responses) {
        int right = 0;
        for(Response response : responses){
            Question questionId = questionRepository.findById(response.getId()).get();
            if(response.getResponse().equalsIgnoreCase(questionId.getRightAnswer())){
                right++;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Your Score: "+ right);
    }
}
