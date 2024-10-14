package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniversiteServiceImplMockTest {

    @Mock
    UniversiteRepository universiteRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    Universite universite;
    Foyer foyer;
    List<Universite> universites;

    @BeforeEach
    public void setUp() {
        foyer = new Foyer(1L, "Foyer ESPRIT", 200, null, null);
        universite = new Universite(1L, "Université ESPRIT", "Ariana", foyer);

        universites = Arrays.asList(
                new Universite(1L, "Université ESPRIT", "Ariana", foyer),
                new Universite(2L, "Université ISG", "Tunis", null)
        );
    }

    @Test
    public void testRetrieveAllUniversites() {
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversite(1L);

        assertNotNull(result);
        assertEquals("Université ESPRIT", result.getNomUniversite());
        assertNotNull(result.getFoyer());
        assertEquals("Foyer ESPRIT", result.getFoyer().getNomFoyer());
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddUniversiteWithFoyer() {
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);

        assertNotNull(result);
        assertEquals("Université ESPRIT", result.getNomUniversite());
        assertEquals("Foyer ESPRIT", result.getFoyer().getNomFoyer());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testModifyUniversiteWithFoyer() {
        Universite updatedUniversite = new Universite(1L, "Université Modifiée", "Ariana", universite.getFoyer());

        when(universiteRepository.save(any(Universite.class))).thenReturn(updatedUniversite);

        Universite result = universiteService.modifyUniversite(updatedUniversite);

        assertEquals("Université Modifiée", result.getNomUniversite());
        assertEquals("Foyer ESPRIT", result.getFoyer().getNomFoyer());
        verify(universiteRepository, times(1)).save(updatedUniversite);
    }



    @Test
    public void testRemoveUniversite() {
        doNothing().when(universiteRepository).deleteById(1L);

        universiteService.removeUniversite(1L);

        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
