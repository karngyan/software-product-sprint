// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public final class FindMeetingQuery {

  /**
   * > 0 : false
   * = 0 : true
   */
  private List<Integer> isAvailableAtMinute;

  /**
   * Worst Case
   * Time Complexity: O(3 * 24 * 60 + events.size() * attendees.size())
   *
   * @param events Collection of Events
   * @param request MeetingRequest
   * @return Collection of potential Time Ranges for Meeting
   */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    // initialize array with true
    isAvailableAtMinute = initializeIsAvailableAtMinuteArray(0);

    // go through events
    events.forEach((event -> {
      Collection<String> intersectionOfAttendees = new HashSet<>(event.getAttendees());
      intersectionOfAttendees.retainAll(request.getAttendees());

      if (intersectionOfAttendees.size() > 0) {
        // Range Updates in difference array
        int start = event.getWhen().start();
        int end = event.getWhen().end();
        isAvailableAtMinute.set(start, isAvailableAtMinute.get(start) + 1);
        isAvailableAtMinute.set(end, isAvailableAtMinute.get(end) - 1);
      }
    }));

    // generate actual array from difference array
    for (int minute = 1; minute < isAvailableAtMinute.size(); ++minute) {
      isAvailableAtMinute.set(minute,
              isAvailableAtMinute.get(minute - 1) + isAvailableAtMinute.get(minute));
    }

    return compressIsAvailableAtMinuteToTimeRanges(request.getDuration());
  }

  private Collection<TimeRange> compressIsAvailableAtMinuteToTimeRanges(long minimumDuration) {
    Collection<TimeRange> potentialMeetingTimeRanges = new ArrayList<>(Collections.emptyList());

    // compress isAvailableAtMinute to TimeRanges
    Boolean isAvailable = false;
    int duration = 0;
    for (int minute = 0; minute < isAvailableAtMinute.size(); ++minute) {
      isAvailable = isAvailableAtMinute.get(minute) == 0;
      if (isAvailable) {
        ++duration;
      } else {
        if (duration >= minimumDuration) {
          TimeRange timeRange = TimeRange.fromStartDuration(minute - duration, duration);
          potentialMeetingTimeRanges.add(timeRange);
        }
        duration = 0;
      }
    }
    if (isAvailable) {
      --duration;
      if (duration >= minimumDuration) {
        TimeRange timeRange = TimeRange.fromStartDuration((24 * 60) - duration, duration);
        potentialMeetingTimeRanges.add(timeRange);
      }
    }

    return potentialMeetingTimeRanges;
  }

  private List<Integer> initializeIsAvailableAtMinuteArray(Integer defaultValue) {
    List<Integer> isAvailableAtMinute = new ArrayList<>();

    while (isAvailableAtMinute.size() <= 24 * 60) {
      isAvailableAtMinute.add(defaultValue);
    }

    return isAvailableAtMinute;
  }
}
