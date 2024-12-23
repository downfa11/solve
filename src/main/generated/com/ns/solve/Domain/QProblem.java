package com.ns.solve.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblem is a Querydsl query type for Problem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblem extends EntityPathBase<Problem> {

    private static final long serialVersionUID = -1820862432L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblem problem = new QProblem("problem");

    public final ListPath<Case, QCase> caseList = this.<Case, QCase>createList("caseList", Case.class, QCase.class, PathInits.DIRECT2);

    public final DateTimePath<java.sql.Timestamp> deadline = createDateTime("deadline", java.sql.Timestamp.class);

    public final StringPath detail = createString("detail");

    public final QMembership membership;

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public final EnumPath<Problem.ProblemStatus> status = createEnum("status", Problem.ProblemStatus.class);

    public QProblem(String variable) {
        this(Problem.class, forVariable(variable), INITS);
    }

    public QProblem(Path<? extends Problem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblem(PathMetadata metadata, PathInits inits) {
        this(Problem.class, metadata, inits);
    }

    public QProblem(Class<? extends Problem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.membership = inits.isInitialized("membership") ? new QMembership(forProperty("membership")) : null;
    }

}

