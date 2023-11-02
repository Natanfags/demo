package com.example.demo.entity.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum TipoSanguineoEnum {
    A_POSITIVO("A+", Arrays.asList("A+", "AB+")),
    A_NEGATIVO("A-", Arrays.asList("A+", "A-", "AB+", "AB-")),
    B_POSITIVO("B+", Arrays.asList("B+", "AB+")),
    B_NEGATIVO("B-", Arrays.asList("B+", "B-", "AB+", "AB-")),
    AB_POSITIVO("AB+", List.of("AB+")),
    AB_NEGATIVO("AB-", Arrays.asList("AB+", "AB-")),
    O_POSITIVO("O+", Arrays.asList("A+", "B+", "O+", "AB+")),
    O_NEGATIVO("O-", List.of("A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"));
    private final String tipoSanguineo;
    private final List<String> tiposCompativeis;

    TipoSanguineoEnum(String tipoSanguineo, List<String> tiposCompativeis) {
        this.tipoSanguineo = tipoSanguineo;
        this.tiposCompativeis = tiposCompativeis;
    }

}
