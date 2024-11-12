package com.example.demo.service;

import java.util.List;

import com.example.demo.models.RegulationHistory;

public interface RegulationHistoryService {

	public List<RegulationHistory> getRegulationHistoryByRegulationId(Integer id);
}
