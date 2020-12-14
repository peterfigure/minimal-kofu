package com.peterfigure.repository

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.data.repository.NoRepositoryBean

// marker interface for spring
@NoRepositoryBean
@EnableR2dbcRepositories(basePackageClasses = [MyReactiveRepository::class])
interface MyReactiveRepository
