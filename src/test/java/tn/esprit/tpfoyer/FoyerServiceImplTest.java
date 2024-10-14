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
        // Initialisation des mocks avant chaque test
        MockitoAnnotations.openMocks(this);

        // Création d'un objet Foyer à utiliser dans les tests
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100L);
    }

    @Test
    public void testRetrieveAllFoyers() {
        // Simulation du comportement du repository
        List<Foyer> foyerList = new ArrayList<>();
        foyerList.add(foyer);

        when(foyerRepository.findAll()).thenReturn(foyerList);

        // Exécution du service
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Vérification des résultats
        assertNotNull(foyers);
        assertEquals(1, foyers.size());
        assertEquals("Foyer A", foyers.get(0).getNomFoyer());

        // Vérification de l'appel de la méthode findAll() une seule fois
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveFoyer() {
        // Simulation du comportement du repository pour findById
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Exécution du service
        Foyer foundFoyer = foyerService.retrieveFoyer(1L);

        // Vérification des résultats
        assertNotNull(foundFoyer);
        assertEquals(1L, foundFoyer.getIdFoyer());
        assertEquals("Foyer A", foundFoyer.getNomFoyer());

        // Vérification de l'appel de la méthode findById()
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddFoyer() {
        // Simulation de la sauvegarde du foyer
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Exécution du service
        Foyer addedFoyer = foyerService.addFoyer(foyer);

        // Vérification des résultats
        assertNotNull(addedFoyer);
        assertEquals("Foyer A", addedFoyer.getNomFoyer());

        // Vérification de l'appel de la méthode save()
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testModifyFoyer() {
        // Simulation de la modification du foyer
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Exécution du service
        Foyer modifiedFoyer = foyerService.modifyFoyer(foyer);

        // Vérification des résultats
        assertNotNull(modifiedFoyer);
        assertEquals("Foyer A", modifiedFoyer.getNomFoyer());

        // Vérification de l'appel de la méthode save()
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testRemoveFoyer() {
        // Simulation de la suppression du foyer par son id
        doNothing().when(foyerRepository).deleteById(1L);

        // Exécution du service
        foyerService.removeFoyer(1L);

        // Vérification de l'appel de la méthode deleteById()
        verify(foyerRepository, times(1)).deleteById(1L);
    }
}
