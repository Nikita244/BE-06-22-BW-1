package it.gruppo6.dao;

import it.gruppo6.entities.Manutenzioni;
import it.gruppo6.util.JpaUtil;


public class ManutenzioneDAO extends JpaUtil {
	public static void save(Manutenzioni object) {
		try {
			t.begin();
			em.persist(object);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto...");
		}
	}
}