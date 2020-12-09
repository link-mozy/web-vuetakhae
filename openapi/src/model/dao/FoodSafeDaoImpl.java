package model.dao;

import model.dto.FoodSafeManualDto;
import model.dto.FoodSafeRecipeDto;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodSafeDaoImpl implements FoodSafeDao {

    @Override
    public int insertFoodSafeRecipe(Connection con, FoodSafeRecipeDto recipeDto) throws SQLException {
        PreparedStatement pstmt = null;
        int result = 0;
        StringBuilder sb = new StringBuilder("insert into recipes ");
        sb.append("(rcp_seq, rcp_nm, hash_tag, att_file_no_main, att_file_no_mk, rcp_parts_dtls)")
                .append(" value(?, ?, ?, ?, ?, ?)");

        try {
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1, recipeDto.getRcpSeq());
            pstmt.setString(2, recipeDto.getRcpNm());
            pstmt.setString(3, recipeDto.getHashTag());
            pstmt.setString(4, recipeDto.getAttFileNoMain());
            pstmt.setString(5, recipeDto.getAttFileNoMk());
            pstmt.setString(6, recipeDto.getRcpPartsDtls());

            result = pstmt.executeUpdate();
        } finally {
            DBUtil.close(null, pstmt, null);
        }

        return result;
    }

    @Override
    public int insertFoodSafeManual(Connection con, FoodSafeManualDto manualDto) throws SQLException {
        PreparedStatement pstmt = null;
        int result = 0;
        StringBuilder sb = new StringBuilder("insert into manuals ");
        sb.append("(manual_id, rcp_seq, manual_text");
        if(manualDto.getManualImg() == null
                || manualDto.getManualImg().isEmpty()
                || manualDto.getManualImg().equals("")
                || manualDto.getManualImg().length() == 0) {
            sb.append(") values(?, ?, ?)");
        }
        else {
            sb.append(", manual_img) values(?, ?, ?, ?)");
        }

        try {
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1, manualDto.getManualId());
            pstmt.setInt(2, manualDto.getRcpSeq());
            pstmt.setString(3, manualDto.getManualText());
            if(manualDto.getManualImg() == null
                    || manualDto.getManualImg().isEmpty()
                    || manualDto.getManualImg().equals("")
                    || manualDto.getManualImg().length() == 0){
            } else {
                pstmt.setString(4, manualDto.getManualImg());
            }

            result = pstmt.executeUpdate();
        } finally {
            DBUtil.close(null, pstmt, null);
        }

        return result;
    }


    /**
     *
     * @param con
     * @param rcpSeq
     * @return 만약 중복되는 값이 있다면 true, 중복되는 값이 없다면 false
     * @throws SQLException
     */
    @Override
    public boolean checkRecipe(Connection con, int rcpSeq) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result = 0;
        String sql = "select count(*) as count from recipes where rcp_seq = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, rcpSeq);

            rset = pstmt.executeQuery();
            rset.next();
            result = rset.getInt(1);
        } finally {
            DBUtil.close(null, pstmt, null);
        }
        return result == 0 ? false : true;
    }


    /**
     *
     * @param con
     * @param manualId
     * @return 만약 중복되는 값이 있다면 true, 중복되는 값이 없다면 false
     * @throws SQLException
     */
    @Override
    public boolean checkManual(Connection con, int manualId, int rcpSeq) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result = 0;
        String sql = "select count(*) as count from manuals where manual_id = ? and rcp_seq = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, manualId);
            pstmt.setInt(2, rcpSeq);

            rset = pstmt.executeQuery();
            rset.next();
            result = rset.getInt(1);
        } finally {
            DBUtil.close(null, pstmt, null);
        }
        return result == 0 ? false : true;
    }
}
