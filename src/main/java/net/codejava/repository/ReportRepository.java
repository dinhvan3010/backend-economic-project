package net.codejava.repository;

import net.codejava.dto.Report;
import net.codejava.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT  brand.name as \"label\",   SUM( inventory.quantity ) as \"count\" , SUM(  inventory.quantity * p.unit_price) as \"sum\"   FROM economicproject.products p " +
            "left join brand brand" +
            "ON brand.id = p.brand_id " +
            "left join inventory" +
            "ON inventory.product_id = p.id " +
            "group by p.brand_id " , nativeQuery = true)
    List<Report> getInventoryDataByBrand();


    @Query(value = "SELECT  category.name as label,   SUM( inventory.quantity ) as count , SUM(  inventory.quantity * p.unit_price) as sum  FROM economicproject.products p \n" +
            "            left join category category" +
            "            ON category.id = p.category_id " +
            "            left join inventory" +
            "            ON inventory.product_id = p.id " +
            "            group by p.category_id " , nativeQuery = true)
    List<Report> getInventoryDataByCate();
}
