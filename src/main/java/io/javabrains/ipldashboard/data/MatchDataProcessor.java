package io.javabrains.ipldashboard.data;

import io.javabrains.*;
import io.javabrains.ipldashboard.data.model.Match;
import net.bytebuddy.asm.Advice.ArgumentHandler.Factory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {

        Match match = new Match();

        
        
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-M-yyyy");
        //System.out(Long.parseLong(matchInput.getId()));
        match.setid(Long.parseLong(matchInput.getId()));
        //System.out.println(((match.getid())));
        match.setCity(matchInput.getCity());
      
        match.setDate(LocalDate.parse(matchInput.getDate(),f));

        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        
        //Set Team 1 & Team 2 depending on innings order
        String firstInningsTeam, secondInningsTeam;

        if("bat".equals(matchInput.getToss_decision())){
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())? matchInput.getTeam2():matchInput.getTeam1();
        }
        else{
            secondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())? matchInput.getTeam2():matchInput.getTeam1();
        }

        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);
        match.setMatchWinner(matchInput.getWinner());
        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        match.setVenue(matchInput.getVenue());
         

        return match;
    }

}