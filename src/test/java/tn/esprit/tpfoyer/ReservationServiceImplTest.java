package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Indique à JUnit d'utiliser Mockito pour la gestion des mocks dans cette classe de test.

public class ReservationServiceImplTest {
    @Mock // Crée un mock du ReservationRepository
    private ReservationRepository reservationRepository;

    @InjectMocks //Crée une instance de ReservationServiceImpl et injecte le mock reservationRepository dedans.
    private ReservationServiceImpl reservationService;

    @BeforeEach //Méthode qui s'exécute avant chaque test.
    public void setUp() {
        //initialise les mocks. MockitoAnnotations.openMocks(this) permet de traiter les annotations @Mock et @InjectMocks.
        MockitoAnnotations.openMocks(this);
    }

    @Test //Prépare les données et le comportement attendu. Vous créez une liste de réservations et configurez le mock pour retourner cette liste lorsque findAll() est appelé.
     void testRetrieveAllReservations() {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("1", new Date(), true, null));
        when(reservationRepository.findAll()).thenReturn(reservations);

        // When //Appelle la méthode à tester,
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Then // Vérifie les résultats. Vous utilisez assertEquals pour vérifier la taille de la liste et l'ID de la réservation. verify s'assure que findAll() a été appelé exactement une fois.
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveReservation() {
        // Given
        Reservation reservation = new Reservation("1", new Date(), true, null);
        when(reservationRepository.findById("1")).thenReturn(Optional.of(reservation));

        // When
        Reservation result = reservationService.retrieveReservation("1");

        // Then
        assertNotNull(result);
        assertEquals("1", result.getIdReservation());
        verify(reservationRepository, times(1)).findById("1");
    }

    @Test
    public void testRetrieveReservation_NotFound() {
        // Given
        when(reservationRepository.findById("1")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            reservationService.retrieveReservation("1");
        });
    }

    @Test
    public void testAddReservation() {
        // Given
        Reservation reservation = new Reservation("1", new Date(), true, null);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // When
        Reservation result = reservationService.addReservation(reservation);

        // Then
        assertNotNull(result);
        assertEquals("1", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testModifyReservation() {
        // Given
        Reservation reservation = new Reservation("1", new Date(), true, null);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // When
        Reservation result = reservationService.modifyReservation(reservation);

        // Then
        assertNotNull(result);
        assertEquals("1", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testRemoveReservation() {
        // When
        reservationService.removeReservation("1");

        // Then
        verify(reservationRepository, times(1)).deleteById("1");
    }

    @Test
    public void testTrouverResSelonDateEtStatus() {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("1", new Date(), true, null));
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(any(Date.class), anyBoolean()))
                .thenReturn(reservations);

        // When
        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(new Date(), true);

        // Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(any(Date.class), anyBoolean());
    }
}
