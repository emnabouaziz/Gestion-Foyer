package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EtudiantServiceImplTest {
    @InjectMocks
    EtudiantServiceImpl etudiantService;
    @Mock
    EtudiantRepository etudiantRepository;
    Etudiant etudiant;
    Reservation reservation;
    List<Etudiant> etudiants;

    @BeforeEach
    public void setUp() {
        reservation = new Reservation("1", new Date(), true);

        Set<Reservation> reservations = new HashSet<>();
        reservations.add(reservation);
        Date dateNaissance = java.sql.Date.valueOf(LocalDate.of(2000, 1, 20));

        etudiant = new Etudiant(1, "etudiant", "Amira",12363256, dateNaissance, reservations);

        etudiants = Arrays.asList(
                new Etudiant(1, "Ben Henda", "Amira", 12363256, dateNaissance, reservations),
                new Etudiant(2, "Zouaoui", "Maryem", 12345678,dateNaissance, reservations)
        );
    }
    @Test
     void testRetrieveAllEtudiants() {
        List< Etudiant>  etudiantList = new ArrayList<>();
        etudiantList.add(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiantList);
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();
        assertEquals(1, etudiants.size());
        verify(etudiantRepository, times(1)).findAll();
    }
    @Test
    void testRetrieveEtudiant() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        Etudiant foundEtudiant = etudiantService.retrieveEtudiant(1L);
        assertNotNull(foundEtudiant);
        assertEquals(etudiant.getIdEtudiant(), foundEtudiant.getIdEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }
    @Test
     void testAddEtudiant() {
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        Etudiant addedEtudiant = etudiantService.addEtudiant(etudiant);
        assertNotNull(addedEtudiant);
        assertEquals(etudiant.getNomEtudiant(), addedEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }
    @Test
    void testModifyEtudiant() {
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        Etudiant modifiedEtudiant = etudiantService.modifyEtudiant(etudiant);
        assertNotNull(modifiedEtudiant);
        assertEquals(etudiant.getNomEtudiant(), modifiedEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }
    @Test
    void testRemoveFoyEtudiant() {
        doNothing().when(etudiantRepository).deleteById(1L);
        etudiantService.removeEtudiant(1L);
        verify(etudiantRepository, times(1)).deleteById(1L);
    }
}
