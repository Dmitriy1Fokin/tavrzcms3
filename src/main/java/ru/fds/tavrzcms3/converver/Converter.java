package ru.fds.tavrzcms3.converver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface Converter<E, D> {

    E toEntity(D dto);
    D toDto(E entity);

    default List<E> toEntity(List<D> dtoList){
        List<E> eList = new ArrayList<>();
        for(D d : dtoList)
            eList.add(toEntity(d));
        return eList;
    }

    default List<D> toDto(List<E> entityList){
        List<D> dList = new ArrayList<>();
        for(E e : entityList)
            dList.add(toDto(e));
        return dList;
    }
}
