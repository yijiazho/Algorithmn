package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PeopleFinder {
    class Meeting {
        int time;
        // participants
        Set<Integer> set;

        public Meeting(int time) {
            this.time = time;
            set = new HashSet<>();
        }
    }

    int[] parent;
    int[] size;

    public void union(int p, int q) {
        if (size[p] < size[q]) {
            union(q, p);
        } else {
            int rootP = root(p);
            int rootQ = root(q);
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }

    public int root(int p) {
        int cur = p;
        while (parent[cur] != cur) {
            parent[cur] = parent[parent[cur]];
            cur = parent[cur];
        }
        parent[p] = cur;
        return cur;
    }

    public boolean find(int p, int q) {
        return root(p) == root(q);
    }

    /**
     * Find all people who share information with person 0 after all meetings,
     * Assuming firstPerson also knows the information.
     * 
     * @param n           total number of people
     * @param meetings    a list of meetings, meetings[i] = {person1, person2, time}
     *                    If multiple meetings happen at the same time, assume they
     *                    are in the same room.
     * @param firstPerson the first person who knows the information
     * @return a list of people who know the information after all meetings
     */
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        // create a meeting action for each timestamp
        Arrays.sort(meetings, (m1, m2) -> {
            if (m1[2] != m2[2]) {
                return m1[2] - m2[2];
            } else {
                return Math.min(m1[0], m1[1]) - Math.min(m2[0], m2[1]);
            }
        });
        List<Meeting> meetingList = new ArrayList<>();
        meetingList.add(new Meeting(-1));
        for (int[] meeting : meetings) {
            Meeting lastMeeting = meetingList.get(meetingList.size() - 1);
            if (lastMeeting.time == meeting[2] &&
                    (lastMeeting.set.contains(meeting[0]) || lastMeeting.set.contains(meeting[1]))) {
                lastMeeting.set.add(meeting[0]);
                lastMeeting.set.add(meeting[1]);
            } else {
                Meeting newMeeting = new Meeting(meeting[2]);
                newMeeting.set.add(meeting[0]);
                newMeeting.set.add(meeting[1]);
                meetingList.add(newMeeting);
            }
        }
        Collections.sort(meetingList, (m1, m2) -> m1.time - m2.time);

        // initialize
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        List<Integer> res = new ArrayList<>();
        union(0, firstPerson);
        res.add(0);
        res.add(firstPerson);

        // for each timestamp, do a union find
        for (int i = 0; i < meetingList.size(); i++) {
            Meeting m = meetingList.get(i);
            boolean hasInformation = false;
            for (int participant : m.set) {
                if (find(0, participant)) {
                    hasInformation = true;
                    break;
                }
            }
            if (hasInformation) {
                for (int participant : m.set) {
                    if (!find(0, participant)) {
                        union(0, participant);
                        res.add(participant);
                    }
                }
            }
        }

        return res;
    }
}
