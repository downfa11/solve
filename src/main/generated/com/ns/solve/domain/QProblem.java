package com.ns.solve.domain;

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

    private static final long serialVersionUID = -818869248L;

    public static final QProblem problem = new QProblem("problem");

    public final ListPath<Case, QCase> caseList = this.<Case, QCase>createList("caseList", Case.class, QCase.class, PathInits.DIRECT2);

    public final DateTimePath<java.sql.Timestamp> deadline = createDateTime("deadline", java.sql.Timestamp.class);

    public final StringPath detail = createString("detail");

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public final EnumPath<Problem.ProblemStatus> status = createEnum("status", Problem.ProblemStatus.class);

    public final StringPath title = createString("title");

    public QProblem(String variable) {
        super(Problem.class, forVariable(variable));
    }

    public QProblem(Path<? extends Problem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblem(PathMetadata metadata) {
        super(Problem.class, metadata);
    }

}

