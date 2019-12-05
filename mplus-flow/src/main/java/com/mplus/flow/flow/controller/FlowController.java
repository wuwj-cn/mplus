package com.mplus.flow.flow.controller;

import java.util.ArrayList;
import java.util.List;

import com.mplus.flow.flow.service.FlowService;
import com.mplus.common.response.Result;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/task")
public class FlowController {

	@Autowired
	private FlowService flowService;

	@RequestMapping(value = "/start/{processKey}", method = RequestMethod.POST)
	public Result<Object> startProcessInstance(@PathVariable String processKey) {
		flowService.startProcess(processKey);
		return Result.success(null);
	}

	@RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<TaskRepresentation>> getTasks(@RequestParam String assignee) {
		List<Task> tasks = flowService.getTasks(assignee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName()));
		}
		return Result.success(dtos);
	}

	static class TaskRepresentation {

		private String id;
		private String name;

		public TaskRepresentation(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
