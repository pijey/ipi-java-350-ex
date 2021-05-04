package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    //Scénarios de test, 1 scénario = 1 test

    //Date d'embauche à aujourd'hui => Nombre années ancienneté : 0
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheToday(){
        //Given
        LocalDate dateEmbaucheToday = LocalDate.now();
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheToday);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(0);
    }

    //Date d'embauche dans le futur => Nombre années ancienneté : null
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheFuture(){
        //Given
        LocalDate dateEmbaucheFuture = LocalDate.now().plusYears(5);
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheFuture);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isNull();
    }

    //Date d'embauche null => Nombre années ancienneté : null
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isNull();
    }

    //Date d'embauche 5 ans dans le passé => Nombre années ancienneté : 5
    @Test
    public void testGetNbAnneesAncienneteDateEmbauchePast(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(5));

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(5);
    }

    @Test
    public void testGetPrimeAnnuelleManagerSansAnciennetePleinTemps(){
        //Given
        // 4 données d'entrée
        LocalDate dateEmbauche = LocalDate.now();
        Integer performance = null;
        String matricule = "M12345";
        Double tempsPartiel = 1.0;
        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Doe", "John", matricule,
                dateEmbauche, Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double primeCalculee = employe.getPrimeAnnuelle();

        //Then
        //1000 * 1.7 = 1700
        Assertions.assertThat(primeCalculee).isEqualTo(1700.0);
    }

    @ParameterizedTest(name = "Employé anciennete {0}, performance {1}, matricule {2}, temps partiel {3} => Prime {4}") //Change l'annotation
    //Rajoute l'annotation contenant les scénarios de test  (réflechir aux dfférents scénarios possibles)
    @CsvSource({
            "0,,'M12345',1.0,1700.0", //Manager à plein temps sans ancienneté
            "0,,'T12345',1.0,1000.0", //Technicien à plein temps sans ancienneté
            //"0,,'M12345',0.5,850.0", //Manager à mi-temps sans ancienneté
            //"5,,'M12345',1.0,2200.0", //Manager à plein temps avec 5 années d'ancienneté
            "0,3,'T12345',1.0,3300.0", //Technicien à plein temps sans ancienneté avec performance 3
    })
    public void testGetPrimeAnnuelle(Integer nbAnneesAnciennete, Integer performance, String matricule, Double tempsPartiel,
                                     Double primeObtenue){
        //Given
        // 4 données d'entrée => remplacer par les paramètres
        LocalDate dateEmbauche = LocalDate.now().minusYears(nbAnneesAnciennete);
        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Doe", "John", matricule,
                dateEmbauche, Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double primeCalculee = employe.getPrimeAnnuelle();

        //Then
        //Remplace la valeur de sortie en dur par le paramètre de sortie
        Assertions.assertThat(primeCalculee).isEqualTo(primeObtenue);
    }

}
