
package com.example.servicio;

import com.example.domain.Individuo;
import java.util.List;

public interface IndividuoServicio {

     
    public List<Individuo> listaIndividuos();
    
    public void buscar (Individuo individuo);
    
    public void guardar (Individuo individuo);
    
    public void salvar(Individuo individuo);
    
    public void borrar (Individuo individuo);
    
    public Individuo localizarIndividuo (Individuo individuo);
    
    
    
}
