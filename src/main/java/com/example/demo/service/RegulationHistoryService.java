package com.example.demo.service;

import java.util.List;

public interface RegulationHistoryService {

	public List<com.example.demo.models.RegulationHistory> getRegulationHistoryByRegulationId(Integer id);
}
