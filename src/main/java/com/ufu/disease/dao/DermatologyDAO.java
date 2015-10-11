package com.ufu.disease.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ufu.disease.to.Chromossomo;

public class DermatologyDAO {

	public void insertRandom() {
		Random r = new Random();

		StringBuilder sq = new StringBuilder();

		sq.append("		INSERT INTO tb_dermatology(	erythema	,scaling	,");
		sq.append("		definite_borders,	itching,	koebner_phenomenon,	polygonal_papules,	");
		sq.append("		follicular_papules	,oral_mucosal_involvement,	knee_and_elbow_involvement,	");
		sq.append("		scalp_involvement,	family_history,	melanin_incontinence,	eosinophils,	");
		sq.append("		pnl_infiltrate,	fibrosis	,exocytosis,	acanthosis,	hyperkeratosis,	");
		sq.append("		parakeratosis,	clubbing	,elongation	,thinning,	spongiform	,munro_microabcess,");
		sq.append("		focal_hypergranulosis,	disappearance,	vacuolisation,	spongiosis	,saw_tooth	,");
		sq.append("		follicular,	perifollicular	,inflammatory	,band_like	,age	,class_disease) ");

		sq.append(" values ");

		sq.append(String.format(
				"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,"
						+ "%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",
				ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), 
				r.nextInt(((1 - 0) + 1) + 0), 
				ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r),
				ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r), ra(r),
				ra(r), ra(r), ra(r), ra(r), ra(r), r.nextInt((99 - 1) + 1) + 0,
				r.nextInt((6 - 0) + 1) + 0));
		// sq.append("(1,2,2,0,3,0,0,0,0,1,0,0,0,0,0,0,3,2,0,0,0,0,0,0,0,0,0,0,3,0,0,0,1,0,55,2);");
	}

	public static int ra(Random r) {
		return r.nextInt((3 - 0) + 1) + 0;
	}

	public List<Chromossomo> searchDermtology(Integer top, Integer claz) {

		try {
			List<Chromossomo> listDerm = new ArrayList<Chromossomo>();

			StringBuilder sq = new StringBuilder();
			sq.append(" select * from tb_dermatology ");
			if(top != null && claz != null) {
				sq.append(String.format(" where class_disease = %d", claz));
				sq.append(String.format(" limit %d", top));
			}
			Connection con = ConnectionUtil.connect();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sq.toString());

			while (rs.next()) {

				Chromossomo d = new Chromossomo();

				d.setIdDermatology(rs.getInt(1));
				d.getErythema().setValue(rs.getInt(2));
				d.getScaling().setValue(rs.getInt(3));
				d.getDefiniteBorders().setValue(rs.getInt(4));
				d.getItching().setValue(rs.getInt(5));
				d.getKoebnerPhenomenon().setValue(rs.getInt(6));
				d.getPolygonalPapules().setValue(rs.getInt(7));
				d.getFollicularPapules().setValue(rs.getInt(8));
				d.getOralMucosalInvolvement().setValue(rs.getInt(9));
				d.getKneeElbowInvolvement().setValue(rs.getInt(10));
				d.getScalpInvolvement().setValue(rs.getInt(11));
				d.getFamilyHistory().setValue(rs.getInt(12));
				d.getMelaninIncontinence().setValue(rs.getInt(13));
				d.getEosinophils().setValue(rs.getInt(14));
				d.getPnlInfiltrate().setValue(rs.getInt(15));
				d.getFibrosis().setValue(rs.getInt(16));
				d.getExocytosis().setValue(rs.getInt(17));
				d.getAcanthosis().setValue(rs.getInt(18));
				d.getHyperkeratosis().setValue(rs.getInt(19));
				d.getParakeratosis().setValue(rs.getInt(20));
				d.getClubbing().setValue(rs.getInt(21));
				d.getElongation().setValue(rs.getInt(22));
				d.getThinning().setValue(rs.getInt(23));
				d.getSpongiform().setValue(rs.getInt(24));
				d.getMunroIcroabcess().setValue(rs.getInt(25));
				d.getFocalHypergranulosis().setValue(rs.getInt(26));
				d.getDisappearance().setValue(rs.getInt(27));
				d.getVacuolisation().setValue(rs.getInt(28));
				d.getSpongiosis().setValue(rs.getInt(29));
				d.getSawYooth().setValue(rs.getInt(30));
				d.getFollicular().setValue(rs.getInt(31));
				d.getPerifollicular().setValue(rs.getInt(32));
				d.getInflammatory().setValue(rs.getInt(33));
				d.getBandLike().setValue(rs.getInt(34));
				d.getAge().setValue(rs.getInt(35));
				d.getClassDisease().setValue(rs.getInt(36));

				listDerm.add(d);
			}

			return listDerm;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
