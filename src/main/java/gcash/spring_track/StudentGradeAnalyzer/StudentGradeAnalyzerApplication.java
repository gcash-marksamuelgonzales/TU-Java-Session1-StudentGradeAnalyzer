package gcash.spring_track.StudentGradeAnalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import services.GradeAnalyzerService;
import vo.StudentVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = {"services"})
public class StudentGradeAnalyzerApplication implements CommandLineRunner {

	@Autowired
	private GradeAnalyzerService gradeAnalyzerService;

	public static void main(String[] args) {
		SpringApplication.run(StudentGradeAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("SWE Spring Track: Laboratory 1 - Student Grade Analyzer");
		gradeAnalyzerService.execute();
	}
}
