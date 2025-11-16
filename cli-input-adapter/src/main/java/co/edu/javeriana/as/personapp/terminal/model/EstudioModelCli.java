package co.edu.javeriana.as.personapp.terminal.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudioModelCli {
    private Integer id_prof;
    private Integer Cc_per;
    private Date fecha;
    private String univer;
}
