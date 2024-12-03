package bespalhuk.kwebflux

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.library.GeneralCodingRules.*
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices

@AnalyzeClasses(
    packages = ["bespalhuk.kwebflux"],
    importOptions = [
        ImportOption.DoNotIncludeArchives::class,
        ImportOption.DoNotIncludeJars::class,
        ImportOption.DoNotIncludeTests::class,
    ],
)
class ArchTest {

    @ArchTest
    val layerRules: ArchRule =
        layeredArchitecture().consideringOnlyDependenciesInLayers()
            .layer("App").definedBy("..app..")
            .layer("AdapterIn").definedBy("..app.adapter.input..")
            .layer("AdapterOut").definedBy("..app.adapter.output..")
            .layer("Core").definedBy("..core..")
            .layer("PortIn").definedBy("..core.port.input..")
            .layer("PortOut").definedBy("..core.port.output..")
            .layer("UseCases").definedBy("..core.usecase..")
            .layer("Services").definedBy("..core.domain.service..")
            .whereLayer("App").mayNotBeAccessedByAnyLayer()
            .whereLayer("UseCases").mayNotBeAccessedByAnyLayer()
            .whereLayer("AdapterIn").mayNotBeAccessedByAnyLayer()
            .whereLayer("AdapterOut").mayNotBeAccessedByAnyLayer()
            .whereLayer("PortIn").mayOnlyBeAccessedByLayers("AdapterIn", "UseCases", "Services")
            .whereLayer("PortOut").mayOnlyBeAccessedByLayers("AdapterOut", "UseCases", "Services")

    @ArchTest
    val dtoRules: ArchRule =
        noClasses().should().haveSimpleNameEndingWith("DTO")
            .andShould().resideInAPackage("..dto..")

    @ArchTest
    val freeOfCycles: ArchRule =
        slices().matching("bespalhuk.kwebflux.(*)..")
            .should().beFreeOfCycles()

    @ArchTest
    val noFieldInjection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION

    @ArchTest
    val noGenericExceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS

    @ArchTest
    val noJodatime = NO_CLASSES_SHOULD_USE_JODATIME
}
