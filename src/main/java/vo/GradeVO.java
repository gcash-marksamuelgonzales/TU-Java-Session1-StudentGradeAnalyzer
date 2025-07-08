package vo;

import lombok.Data;

@Data
public class GradeVO {
    private Double averageGrade;
    private Integer countA;
    private Integer countB;
    private Integer countC;
    private Integer countD;
    private Integer countF;
    private String topStudent;
    private Double topGrade;
}
