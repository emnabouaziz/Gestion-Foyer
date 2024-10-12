package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoyerServiceImplTest {

    @InjectMocks
    FoyerServiceImpl foyerService;

    @Mock
    FoyerRepository foyerRepository;

    Foyer foyer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100L);
    }

    @Test
    public void testRetrieveAllFoyers() {
        List<Foyer> foyerList = new ArrayList<>();
        foyerList.add(foyer);

        when(foyerRepository.findAll()).thenReturn(foyerList);

        List<Foyer> foyers = foyerService.retrieveAllFoyers();
        assertEquals(1, foyers.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveFoyer() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer foundFoyer = foyerService.retrieveFoyer(1L);
        assertNotNull(foundFoyer);
        assertEquals(foyer.getIdFoyer(), foundFoyer.getIdFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveFoyer_NotFound() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        Foyer foundFoyer = foyerService.retrieveFoyer(1L);
        assertNull(foundFoyer); // Vérifie que la méthode retourne null ou un comportement attendu
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddFoyer() {
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer addedFoyer = foyerService.addFoyer(foyer);
        assertNotNull(addedFoyer);
        assertEquals(foyer.getNomFoyer(), addedFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testAddFoyer_Null() {
        Foyer nullFoyer = null;
        assertThrows(IllegalArgumentException.class, () -> {
            foyerService.addFoyer(nullFoyer); // Supposons que vous lanciez une exception pour une entrée nulle
        });
    }

    @Test
    public void testModifyFoyer() {
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer modifiedFoyer = foyerService.modifyFoyer(foyer);
        assertNotNull(modifiedFoyer);
        assertEquals(foyer.getNomFoyer(), modifiedFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testModifyFoyer_NotFound() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        Foyer modifiedFoyer = foyerService.modifyFoyer(foyer);
        assertNull(modifiedFoyer); // Vérifiez le comportement attendu
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    public void testRemoveFoyer() {
        doNothing().when(foyerRepository).deleteById(1L);

        foyerService.removeFoyer(1L);
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRemoveFoyer_NotFound() {
        // Si votre service a une logique qui vérifie d'abord l'existence
        doNothing().when(foyerRepository).deleteById(1L);

        foyerService.removeFoyer(1L);
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAddFoyer_Exception() {
        when(foyerRepository.save(any(Foyer.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            foyerService.addFoyer(foyer);
        });
        verify(foyerRepository, times(1)).save(foyer);
    }
}
