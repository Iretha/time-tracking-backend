package com.smdev.timetracking.ttbackend.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "time_intervals")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TrackedTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "summary", length = 255)
    private String summary;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

}
