package tn.esprit.tpfoyer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ChambreServiceImplTest {
    @Mock
    ChambreRepository chambreRepository;
    @InjectMocks
    ChambreServiceImpl chambreService;
    Chambre chambre;
    Bloc bloc;
    List<Chambre> listChambres;

    @BeforeEach
    void setUp() {
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(bloc);
        chambre.setReservations(new HashSet<>());

        listChambres = Arrays.asList(chambre);
    }

    @Test
    void testRetrieveChambre() {
        when(chambreRepository.findById(anyLong())).thenReturn(Optional.of(chambre));

        Chambre retrievedChambre = chambreService.retrieveChambre(1L);

        assertNotNull(retrievedChambre);
        assertEquals(101, retrievedChambre.getNumeroChambre());
        assertEquals("Bloc A", retrievedChambre.getBloc().getNomBloc());

        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveAllChambres() {
        when(chambreRepository.findAll()).thenReturn(listChambres);
        List<Chambre> chambres = chambreService.retrieveAllChambres();
        assertNotNull(chambres);
        assertEquals(1, chambres.size());
        assertEquals(101, chambres.get(0).getNumeroChambre());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testAddChambre() {
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);
        Chambre addedChambre = chambreService.addChambre(chambre);

        assertNotNull(addedChambre);
        assertEquals(101, addedChambre.getNumeroChambre());

        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testModifyChambre() {
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre modifiedChambre = chambreService.modifyChambre(chambre);

        assertNotNull(modifiedChambre);
        assertEquals(101, modifiedChambre.getNumeroChambre());

        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testRemoveChambre() {
        chambreService.removeChambre(1L);

        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRecupererChambresSelonTyp() {
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(listChambres);

        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        assertNotNull(chambres);
        assertEquals(1, chambres.size());
        assertEquals(101, chambres.get(0).getNumeroChambre());

        verify(chambreRepository, times(1)).findAllByTypeC(TypeChambre.SIMPLE);
    }

    @Test
    void testTrouverChambreSelonEtudiant() {
        long cin = 123456;

        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre);

        Chambre foundChambre = chambreService.trouverchambreSelonEtudiant(cin);

        assertNotNull(foundChambre);
        assertEquals(101, foundChambre.getNumeroChambre());

        verify(chambreRepository, times(1)).trouverChselonEt(cin);
    }
}
