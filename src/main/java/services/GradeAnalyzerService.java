package services;

import org.springframework.stereotype.Service;
import vo.GradeVO;
import vo.StudentVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class GradeAnalyzerService {

    public void execute(){

        // Enter Total Student Roster
        Scanner scanner = new Scanner(System.in);
        Integer studentCount;
        while(true){
            System.out.println("Enter number of students: ");
            if(scanner.hasNextInt()){
                studentCount = scanner.nextInt();
                break;
            }
            else{
                System.out.println("Invalid input. Kindly retry..");
                scanner.next();
            }
        }

        // Process Student List
        if(studentCount > 0){
            List<StudentVO> studentVOs = processStudentList(studentCount);
            GradeVO gradeVO = processGrades(studentVOs);

            if(gradeVO.getAverageGrade() > 0){
                System.out.println("----- Class Summary -----");
                System.out.println(String.format("Average Score: %s",gradeVO.getAverageGrade()));
                System.out.println(String.format("Grade Counts: A:%s, B:%s, C:%s, D:%s, F:%s",gradeVO.getCountA(),gradeVO.getCountB(),gradeVO.getCountC(),gradeVO.getCountD(),gradeVO.getCountF()));
                System.out.println(String.format("Top Student(s): %s (%s)", gradeVO.getTopStudent(), gradeVO.getTopGrade()));
            }

        } else{
            System.out.println("Total Student Roster Count is 0..");
        }

        System.exit(0);
    }

    public List<StudentVO> processStudentList(Integer studentCount){
        List<StudentVO> studentVOs = new ArrayList<>();
        Boolean isComplete = false;

        for(int i = 1; i < studentCount+1; i++){
            // Initial encoding of students
            StudentVO studentVO = new StudentVO();
            studentVO.setStudentName(inputStudentName(i));
            studentVO.setStudentGrade(inputStudentGrade(studentVO.getStudentName()));
            studentVO.setStudentLetterGrade(processStudentLetterGrade(studentVO.getStudentName(), studentVO.getStudentGrade()));
            studentVOs.add(studentVO);
        }

        return studentVOs;
    }

    public String inputStudentName(Integer studentCount){
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("Enter name of Student %s",studentCount));
        String studentName = scanner.nextLine();
        return studentName;
    }

    public Double inputStudentGrade(String studentName){
        Scanner scanner = new Scanner(System.in);
        Double studentGrade;
        while(true){
            System.out.println(String.format("Enter score for %s: ",studentName));
            if(scanner.hasNextDouble()){
                studentGrade = scanner.nextDouble();
                break;
            }
            else{
                System.out.println("Invalid input for grade. Kindly retry.");
                scanner.next();
            }
        }
        return studentGrade;
    }

    public String processStudentLetterGrade(String studentName, Double studentGrade){
        int studentGrade1 = (int) Math.floor(studentGrade);
        String studentLetterGrade = switch ( studentGrade1 / 10) {
            case 10, 9 -> "A";
            case 8 -> "B";
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };
        System.out.println(String.format("%s got grade: %s",studentName,studentLetterGrade));
        return studentLetterGrade;
    }

    public GradeVO processGrades(List<StudentVO> studentVOs){
        StringBuilder topStudent = new StringBuilder();
        Double topGrade = 0.00;
        Integer totalA = 0;
        Integer totalB = 0;
        Integer totalC = 0;
        Integer totalD = 0;
        Integer totalF = 0;

        // Process Average Grade
        List<Double> averageGradeList = studentVOs.stream()
                .map(StudentVO::getStudentGrade)
                .toList();

        Double averageGrade = averageGradeList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        for(StudentVO studentVO : studentVOs){
            // Process Top Student
            Double studentGrade = studentVO.getStudentGrade();
            if(studentGrade !=null && studentGrade > topGrade){
                topStudent.append(studentVO.getStudentName());
                topGrade = studentGrade;
            } else if (studentGrade == topGrade){
                topStudent.append(", " +studentVO.getStudentName());
            }

            // Process Grade Count
            switch(studentVO.getStudentLetterGrade()){
                case "A":
                    totalA++;
                    break;
                case "B":
                    totalB++;
                    break;
                case "C":
                    totalC++;
                    break;
                case "D":
                    totalD++;
                    break;
                default:
                    totalF++;
            }

        }

        GradeVO gradeVO = new GradeVO();
        gradeVO.setTopStudent(topStudent.toString());
        gradeVO.setTopGrade(topGrade);
        gradeVO.setCountA(totalA);
        gradeVO.setCountB(totalB);
        gradeVO.setCountC(totalC);
        gradeVO.setCountD(totalD);
        gradeVO.setCountF(totalF);
        gradeVO.setAverageGrade(averageGrade);

        return gradeVO;
    }

}
