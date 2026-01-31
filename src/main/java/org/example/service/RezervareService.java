package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.example.entity.Slot;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RezervareService {

    @Transactional
    public void initDatabase() {
        if (Slot.count() == 0) {
            for (int i = 9; i <= 18; i++) {
                new Slot(i).persist();
            }
            System.out.println("--> Baza de date a fost populata (9:00 - 18:00).");
        }
    }

    @Transactional
    public String getSloturiDisponibile() {
        List<Slot> sloturi = Slot.listAll();
        return sloturi.stream()
                .map(s -> "Ora " + s.ora + ": " + (s.esteLiber ? "LIBER" : "OCUPAT"))
                .collect(Collectors.joining("\n"));
    }

    @Transactional
    public String getRezervarileMele(String clientToken) {
        List<Slot> aleMele = Slot.list("rezervatDe", clientToken);
        if (aleMele.isEmpty()) return "Nu ai nicio rezervare.";

        return aleMele.stream()
                .map(s -> "Ora " + s.ora)
                .collect(Collectors.joining(", "));
    }

    @Transactional
    public synchronized String rezervaLoc(int ora, String clientToken) {
        Slot slot = Slot.find("ora", ora).firstResult();

        if (slot == null) return "Ora invalida (doar 9-18).";
        if (!slot.esteLiber) return "Imi pare rau, locul a fost deja luat!";

        slot.esteLiber = false;
        slot.rezervatDe = clientToken;
        return "SUCCES! Ai rezervat ora " + ora;
    }

    @Transactional
    public String anuleazaRezervare(int ora, String clientToken) {
        Slot slot = Slot.find("ora", ora).firstResult();

        if (slot == null) return "Ora invalida.";
        if (slot.esteLiber) return "Locul este deja liber.";
        if (!clientToken.equals(slot.rezervatDe)) return "Eroare: Nu poti anula rezervarea altcuiva!";

        slot.esteLiber = true;
        slot.rezervatDe = null;
        return "Rezervare anulata cu succes.";
    }
}