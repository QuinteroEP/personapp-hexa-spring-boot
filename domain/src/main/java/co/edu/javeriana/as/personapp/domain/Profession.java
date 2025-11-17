package co.edu.javeriana.as.personapp.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Profession {
	@NonNull
	private Integer identification;
	@NonNull
	private String name;
	private String description;
	@ToString.Exclude
	@JsonIgnore
	private List<Study> studies;
}
