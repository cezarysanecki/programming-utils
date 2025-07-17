package pl.cezarysanecki.utils.decider.heroes

import spock.lang.Specification

import static pl.cezarysanecki.utils.decider.heroes.DwellingCommand.RecruitCreature
import static pl.cezarysanecki.utils.decider.heroes.DwellingEvent.AvailableCreaturesChanged
import static pl.cezarysanecki.utils.decider.heroes.DwellingEvent.CreatureRecruited
import static pl.cezarysanecki.utils.decider.heroes.Resource.CRYSTAL
import static pl.cezarysanecki.utils.decider.heroes.Resource.GOLD

class DwellingDeciderSpec extends Specification {

    private def dwellingId = UUID.randomUUID()
    private def angelId = UUID.randomUUID()
    private def portalOfGlory = new DwellingDecider(
            angelId,
            new Cost([
                    (GOLD)   : 3000,
                    (CRYSTAL): 1,
            ])
    )

    def "given Dwelling with 2 creatures, when recruit 2 creature, then recruited 2 and totalCost = costPerTroop * 2"() {
        given:
        def givenEvents = [(new AvailableCreaturesChanged(dwellingId, angelId, 2))]
        and:
        def whenCommand = new RecruitCreature(dwellingId, angelId, 2)

        when:
        def thenEvents = portalOfGlory.decide(whenCommand, givenEvents)

        then:
        thenEvents == [new CreatureRecruited(
                dwellingId,
                angelId,
                2,
                new Cost([
                        (GOLD)   : 6000,
                        (CRYSTAL): 2,
                ])
        )]
    }

}
