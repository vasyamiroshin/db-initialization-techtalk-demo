package com.epam.vm.techtalk.dbinitialization.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.epam.vm.techtalk.dbinitialization.domain.InitializationStrategy;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

	// Item writer for different file types
	// Reads data
	@Bean
	public FlatFileItemReader<InitializationStrategy> reader() {
		FlatFileItemReader<InitializationStrategy> reader = new FlatFileItemReader<InitializationStrategy>();
		reader.setResource(new ClassPathResource("init-data.csv"));
		reader.setLineMapper(new DefaultLineMapper<InitializationStrategy>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "id", "name", "rate" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<InitializationStrategy>() {
					{
						setTargetType(InitializationStrategy.class);
					}
				});
			}
		});
		return reader;
	}

	// Processor for transformation of data from input to data for output
	@Bean
	public PassThroughItemProcessor<InitializationStrategy> processor() {
		return new PassThroughItemProcessor<InitializationStrategy>();
	}

	// Writer for jpa entities
	@Bean
	public ItemWriter<InitializationStrategy> writer() {
		JpaItemWriter<InitializationStrategy> writer = new JpaItemWriter<InitializationStrategy>();
		writer.setEntityManagerFactory(entityManagerFactory.getObject());
		return writer;
	}
	
	
	// Job definition
	@Bean
    public Job importInitialStrategyJob() {
        return jobBuilderFactory.get("importInitialStrategyJob")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

	
	// Step definition
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<InitializationStrategy, InitializationStrategy> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
