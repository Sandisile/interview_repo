package rest.interviewApp.dto;

import java.util.List;

import services.interviewApp.jpa.exception.NotFoundException;

public interface DTO 
{
    public List<?> getAll();
    public Object findById(int id) throws NotFoundException;
    public void update(Object obj);
    public void delete(int obj) throws NotFoundException;
    public void add(Object obj);
    public List<?> search(Object obj);
}
