package com.carrotsearch.ant.tasks.junit4.events.aggregated;

import java.util.Collections;
import java.util.List;

import org.junit.runner.Description;

import com.carrotsearch.ant.tasks.junit4.SlaveInfo;
import com.carrotsearch.ant.tasks.junit4.events.IEvent;
import com.carrotsearch.ant.tasks.junit4.events.mirrors.FailureMirror;

public class AggregatedSuiteResultEvent implements AggregatedResultEvent {
  private final SlaveInfo slave;
  private final Description description;
  private final List<AggregatedTestResultEvent> tests;
  private final List<FailureMirror> suiteFailures;
  private final List<IEvent> eventStream;

  public AggregatedSuiteResultEvent(SlaveInfo id, Description description, 
      List<FailureMirror> suiteFailures, List<AggregatedTestResultEvent> tests,
      List<IEvent> eventStream) {
    this.slave = id;
    this.tests = tests;
    this.suiteFailures = suiteFailures;
    this.description = description;
    this.eventStream = eventStream;
  }

  public List<AggregatedTestResultEvent> getTests() {
    return tests;
  }

  @Override
  public List<FailureMirror> getFailures() {
    return Collections.unmodifiableList(suiteFailures);
  }

  @Override
  public boolean isSuccessful() {
    if (!suiteFailures.isEmpty())
      return false;

    for (AggregatedTestResultEvent e : tests) {
      if (!e.isSuccessful()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public List<IEvent> getEventStream() {
    return eventStream;
  }
  
  @Override
  public SlaveInfo getSlave() {
    return slave;
  }

  @Override
  public Description getDescription() {
    return description;
  }
}