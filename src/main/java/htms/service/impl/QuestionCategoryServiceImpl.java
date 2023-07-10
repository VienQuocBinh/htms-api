package htms.service.impl;

import htms.api.request.QuestionCategoryRequest;
import htms.api.response.QuestionCategoryResponse;
import htms.model.Program;
import htms.model.QuestionCategory;
import htms.model.Trainer;
import htms.repository.ProgramContentRepository;
import htms.repository.ProgramRepository;
import htms.repository.QuestionCategoryRepository;
import htms.repository.TrainerRepository;
import htms.service.QuestionCategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionCategoryServiceImpl implements QuestionCategoryService {

    private final QuestionCategoryRepository repository;
    private final TrainerRepository trainerRepository;
    private final ProgramRepository programRepository;
    private final ProgramContentRepository programContentRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<QuestionCategoryResponse> getAllByTrainerAndProgram(UUID trainerId, UUID programId) {
        //validate input
        var trainer = trainerRepository.findById(trainerId).orElseThrow(EntityNotFoundException::new);
        var program = programRepository.findById(programId).orElseThrow(EntityNotFoundException::new);
        log.debug("trainer found with name: " + trainer.getName() + ", and program " + program.getName());
        var result = repository.findAllByTrainerAndProgram(trainer, program);
        List<QuestionCategoryResponse> responses = Collections.emptyList();
        if(result.isPresent() && !result.get().isEmpty()) {
            log.debug("category list found with " + result.get().size() + " entities");
            responses = result.get().stream().map(category -> modelMapper.map(category, QuestionCategoryResponse.class)).toList();
        } else {
            var programContent = programContentRepository.findById_Program_IdAndId_Trainer_Id(programId, trainerId).orElseThrow(EntityNotFoundException::new);
            //Create default category if programContent exist.
            if (programContent != null) {
                log.debug("programCotent found but no default category. Creating default category for " + program.getName());
                var defaultCategory = createDefaultCategory(program, trainer);
                defaultCategory = repository.save(defaultCategory);
                responses = List.of(modelMapper.map(defaultCategory, QuestionCategoryResponse.class));
            }
        }
        return responses;
    }

    @Override
    public QuestionCategoryResponse getQuestionCategoryDetail(UUID id) {
        //validate input
        var category = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(category, QuestionCategoryResponse.class);
    }

    @Override
    public QuestionCategoryResponse saveNewCategory(QuestionCategoryRequest request) throws Exception {
        var isExisted = request.getNumberId() != null && repository.existsByNumberIdAndProgram_Id(request.getNumberId(), request.getProgramId());
        QuestionCategory.QuestionCategoryBuilder categoryBuilder;
        QuestionCategory category;
        if (isExisted) {
            throw new Exception("Category with Number Id are existed in this program");
        } else {
//            categoryBuilder = modelMapper.map(request, QuestionCategory.class);
            var program = programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new);
            var trainer = trainerRepository.findById(request.getTrainerId()).orElseThrow(EntityNotFoundException::new);
            var parent = repository.findById(request.getParent());
            categoryBuilder = QuestionCategory.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .numberId(request.getNumberId())
                    .program(program)
                    .trainer(trainer)
                    .createdDate(Date.from(Instant.now()))
                    .modifiedDate(Date.from(Instant.now()));
            if(parent.isPresent()) {
                categoryBuilder.parent(parent.get());
            } else {
                var programContent = programContentRepository.findById_Program_IdAndId_Trainer_Id(request.getProgramId(), request.getTrainerId());
                if(programContent.isPresent()) {
                    var defaultCategory = createDefaultCategory(program, trainer);
                    defaultCategory = repository.save(defaultCategory);
                    categoryBuilder.parent(defaultCategory);
                }
            }
            category = repository.save(categoryBuilder.build());
        }
        return modelMapper.map(category, QuestionCategoryResponse.class);
    }

    @Override
    public QuestionCategoryResponse update(QuestionCategoryRequest request) throws Exception {
        if(request.getId() == null) {
            throw new Exception("id must not be null");
        }
        var category = repository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setNumberId(request.getNumberId());
        if (category.getParent().getId() != request.getParent()) {
            var parent = repository.findById(request.getParent()).orElseThrow(EntityNotFoundException::new);
            category.setParent(parent);
        }
        category.setModifiedDate(Date.from(Instant.now()));
        category = repository.save(category);
        return modelMapper.map(category, QuestionCategoryResponse.class);
    }

    @Override
    public QuestionCategoryResponse delete(UUID id) {
        var category = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(category);
        return modelMapper.map(category, QuestionCategoryResponse.class);
    }

    private QuestionCategory createDefaultCategory(Program program, Trainer trainer) {
        return QuestionCategory.builder()
                .name("Default for " + program.getName())
                .trainer(trainer)
                .program(program)
                .createdDate(Date.from(Instant.now()))
                .modifiedDate(Date.from(Instant.now()))
                .build();
    }
}
