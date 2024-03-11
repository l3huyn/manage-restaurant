package com.project.restaurant.services;

import com.project.restaurant.dtos.DiningTableDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.DiningTable;
import com.project.restaurant.repositories.DiningTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DiningTableService implements IDiningTableService{

    //DI
    private final DiningTableRepository diningTableRepository;

    @Override
    public DiningTable createDiningTable(DiningTableDTO diningTableDTO) {
        //Convert từ DiningTableDTO sang DiningTable
        DiningTable newDiningTable = DiningTable.builder()
                .tableNumber(diningTableDTO.getTableNumber())
                .capacity(diningTableDTO.getCapacity())
                .status(diningTableDTO.getStatus())
                .build();

        //Lưu vào DB
        return diningTableRepository.save(newDiningTable);
    }


    @Override
    public DiningTable getDiningTableById(long id) throws Exception {
        return diningTableRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find dining table with id: " + id));
    }



    @Override
    public List<DiningTable> getAllDiningTables() {
        return diningTableRepository.findAll();
    }



    @Override
    public DiningTable updateDiningTable(long id, DiningTableDTO diningTableDTO) throws Exception {
        DiningTable existingDiningTable = getDiningTableById(id);

        if(existingDiningTable != null) {
            //Gán các giá trị
            existingDiningTable.setTableNumber(diningTableDTO.getTableNumber());
            existingDiningTable.setCapacity(diningTableDTO.getCapacity());
            existingDiningTable.setStatus(diningTableDTO.getStatus());
        }
        return diningTableRepository.save(existingDiningTable);
    }

    @Override
    public void deleteDiningTable(long id) {
        Optional<DiningTable> optionalDiningTable = diningTableRepository.findById(id);
        optionalDiningTable.ifPresent(diningTableRepository::delete);
    }
}
