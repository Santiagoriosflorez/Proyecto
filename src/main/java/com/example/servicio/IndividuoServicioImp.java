
package com.example.servicio;

import com.example.dao.IndividuoDao;
import com.example.domain.Individuo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class IndividuoServicioImp implements IndividuoServicio {
    
    
    
    
    @Autowired
    private IndividuoDao individuoDao;
    
    
    @Override
    @Transactional (readOnly= true)
    public List<Individuo> listaIndividuos() {
        return (List<Individuo>) individuoDao.findAll();
    }
    
   
    
    @Override
    @Transactional
    public void guardar(Individuo individuo) {
         individuoDao.delete(individuo);
    }

    @Override
    @Transactional
    public void salvar(Individuo individuo) {
        individuoDao.save(individuo);
    }

    @Override
    @Transactional
    public void buscar(Individuo individuo) {
         individuoDao.save(individuo);
    }

    @Override
    @Transactional (readOnly = true)
    public Individuo localizarIndividuo(Individuo individuo) {
        return individuoDao.findById(individuo.getId_individuo()).orElse(null);
    }

    @Override
    public void borrar(Individuo individuo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
