package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BlocServiceImplTest {

    @Mock
    BlocRepository blocRepository;

    @InjectMocks
    BlocServiceImpl blocService;

    Bloc bloc1;
    Bloc bloc2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(100);

        bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(50);
    }

    @Test
    public void testRetrieveAllBlocs() {
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        List<Bloc> result = blocService.retrieveAllBlocs();

        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveBloc() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc1));

        Bloc result = blocService.retrieveBloc(1L);

        assertEquals(bloc1, result);
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddBloc() {
        when(blocRepository.save(bloc1)).thenReturn(bloc1);

        Bloc result = blocService.addBloc(bloc1);

        assertEquals(bloc1, result);
        verify(blocRepository, times(1)).save(bloc1);
    }

    @Test
    public void testModifyBloc() {
        when(blocRepository.save(bloc1)).thenReturn(bloc1);

        Bloc result = blocService.modifyBloc(bloc1);

        assertEquals(bloc1, result);
        verify(blocRepository, times(1)).save(bloc1);
    }

    @Test
    public void testRemoveBloc() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.removeBloc(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetrieveBlocsSelonCapacite() {
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(60);

        assertEquals(1, result.size()); // Only bloc1 has capacity greater than 60
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    public void testFindBlocsWithoutFoyer() {
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(Arrays.asList(bloc1));

        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        assertEquals(1, result.size());
        verify(blocRepository, times(1)).findAllByFoyerIsNull();
    }

    @Test
    public void testFindBlocsByNameAndCapacity() {
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 100)).thenReturn(Arrays.asList(bloc1));

        List<Bloc> result = blocService.trouverBlocsParNomEtCap("Bloc A", 100);

        assertEquals(1, result.size());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc("Bloc A", 100);
    }
}
