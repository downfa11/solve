package com.ns.solve.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlgorithmProblem is a Querydsl query type for AlgorithmProblem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlgorithmProblem extends EntityPathBase<AlgorithmProblem> {

    private static final long serialVersionUID = -1618406562L;

    public static final QAlgorithmProblem algorithmProblem = new QAlgorithmProblem("algorithmProblem");

    public final QProblem _super = new QProblem(this);

    //inherited
    public final NumberPath<Integer> attemptCount = _super.attemptCount;

    public final ListPath<Case, QCase> caseList = this.<Case, QCase>createList("caseList", Case.class, QCase.class, PathInits.DIRECT2);

    //inherited
    public final ListPath<com.ns.solve.domain.Comment, com.ns.solve.domain.QComment> commentList = _super.commentList;

    //inherited
    public final NumberPath<Double> correctCount = _super.correctCount;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath creator = _super.creator;

    //inherited
    public final NumberPath<Double> entireCount = _super.entireCount;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath inputOutputSpecification = createString("inputOutputSpecification");

    //inherited
    public final BooleanPath isChecked = _super.isChecked;

    public final NumberPath<Long> level = createNumber("level", Long.class);

    //inherited
    public final StringPath reviewer = _super.reviewer;

    //inherited
    public final StringPath solution = _super.solution;

    //inherited
    public final StringPath source = _super.source;

    //inherited
    public final ListPath<String, StringPath> tags = _super.tags;

    //inherited
    public final StringPath title = _super.title;

    //inherited
    public final StringPath type = _super.type;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAlgorithmProblem(String variable) {
        super(AlgorithmProblem.class, forVariable(variable));
    }

    public QAlgorithmProblem(Path<? extends AlgorithmProblem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlgorithmProblem(PathMetadata metadata) {
        super(AlgorithmProblem.class, metadata);
    }

}

