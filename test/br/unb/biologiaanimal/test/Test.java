package br.unb.biologiaanimal.test;

import br.unb.biologiaanimal.edf.EDF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class Test
{
    @Test
    private static void AbrirArquivo()
    {
        EDF edf = new EDF("data\\linhadebase.edf");
    }
}
