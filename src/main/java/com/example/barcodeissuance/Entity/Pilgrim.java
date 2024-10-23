	package com.example.barcodeissuance.Entity;

	import java.time.LocalDateTime;
	import java.util.UUID;

	import jakarta.persistence.*;
	import lombok.*;
	import org.hibernate.annotations.CreationTimestamp;

	@Entity
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public class Pilgrim {

		@Id
		private Long barcode;
		private String name;
		private String gender;
		private Long adhaarNo;
		private Integer age;
		private LocalDateTime createdDate;
		private boolean scanned;
	}
