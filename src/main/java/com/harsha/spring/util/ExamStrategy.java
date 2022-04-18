package com.harsha.spring.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.harsha.spring.models.Question;

public class ExamStrategy {

	public static List<Question> generateQuestionPaper(int numberOfQuestions, int numberOfQuestionsInExam,
			List<Question> sectionquestions) {
		Random random = new Random();
		Set<Integer> randomValues = new HashSet<>();
		List<Question> filteredQuestions = new ArrayList<>();
		
		if (numberOfQuestions == numberOfQuestionsInExam) {
			return sectionquestions;
		}
		
		
		//generating random values for questions
		while(randomValues.size() < numberOfQuestionsInExam) {
			int randomvalue = random.nextInt(numberOfQuestions);
			randomValues.add(randomvalue);
		}
		
		randomValues.stream().forEach(value ->{
			Question question = sectionquestions.get(value);
			filteredQuestions.add(question);
		});
		
		return filteredQuestions;
	}
}
