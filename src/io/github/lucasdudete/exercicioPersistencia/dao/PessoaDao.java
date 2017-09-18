/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.lucasdudete.exercicioPersistencia.dao;

import io.github.lucasdudete.exercicioPersistencia.model.Pessoa;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucasduete
 */
public class PessoaDao {
    
    private File arquivo;
    
    public PessoaDao() throws IOException {
        arquivo = new File("Pessoas.bin");
        
        if(!arquivo.exists())
            arquivo.createNewFile();
    }
    
    public List<Pessoa> listar() throws IOException, ClassNotFoundException {
        if(arquivo.length() == 0){
            return new ArrayList<>();
        }
        
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo));
        
        return (List<Pessoa>) in.readObject();
    }
    
    public boolean salvar(Pessoa p) throws IOException, ClassNotFoundException {
        List<Pessoa> pessoas = listar();
        
        if (buscar(p.getCpf()) == null) {
            pessoas.add(p);
            atualizaArquivo(pessoas);
            return true;
        } else {
            return false;
        }
        
    }
    
    public Pessoa buscar(String cpf) throws IOException, ClassNotFoundException {
        List<Pessoa> pessoas = listar();
        
        for(Pessoa p: pessoas) {
            if(p.getCpf().equals(cpf)){
                return p;
            }
        }
        return null;
    }
    
    private boolean verificaCpf(List<Pessoa> pessoas, String cpf) {
        for(Pessoa p: pessoas) {
            if(p.getCpf().equals(cpf)){
                return false;
            }
        }
        return true;
    }
    
    private void atualizaArquivo(List<Pessoa> pessoas) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo));
        
        out.writeObject(pessoas);
        out.close();
    }
    
}
