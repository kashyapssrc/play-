package com.objectfrontier.training.java.services.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.objectfrontier.training.java.services.pojo.Address;
import com.objectfrontier.training.java.services.pojo.Person;

public class DataManager {

    public Object[][] getCreatePositiveData() throws IOException, URISyntaxException {

        Path filePath = Paths.get(ClassLoader.getSystemResource("createPositive.csv")
                                             .toURI());

        Stream<String> lines = Files.lines(filePath);
        List<String[]> readPersonList = lines.map(persons -> persons.split(","))
                                             .collect(Collectors.toList());
        List<Person> personList = new ArrayList<>();
        List<Person> expectedPersonList = new ArrayList<>();
        lines.close();

        for (String[] strings : readPersonList) {

            Address address = new Address(strings[4], strings[5], Integer.parseInt(strings[6]));
            Person actualPerson = new Person(strings[0],
                                             strings[1],
                                             strings[2],
                                             strings[3],
                                             address);
            Person expectedPerson = actualPerson;
            personList.add(actualPerson);
            expectedPersonList.add(expectedPerson);
        }

        Object[][] personArray = new Object[personList.size()][2];
        for(int i = 0; i < personList.size(); i++) {

            personArray[i][0] = personList.get(i);
            personArray[i][1] = expectedPersonList.get(i);
        }
        return personArray;
    }

    public Object[][] getCreateNegativeData() throws IOException, URISyntaxException {

        Path filePath = Paths.get(ClassLoader.getSystemResource("createNegative.csv")
                                             .toURI());

        Stream<String> lines = Files.lines(filePath);
        List<String[]> readPersonList = lines.map(persons -> persons.split(","))
                                             .collect(Collectors.toList());
        List<Person> personList = new ArrayList<>();
        List<Integer> exceptionList = new ArrayList<>();
        lines.close();

        for (String[] strings : readPersonList) {

            Address address = new Address(strings[4], strings[5], Integer.parseInt(strings[6]));
            Person actualPerson = new Person(strings[0],
                                             strings[1],
                                             strings[2],
                                             strings[3],
                                             address);

            int exception = Integer.parseInt(strings[7]);
            personList.add(actualPerson);
            exceptionList.add(exception);
        }

        Object[][] personArray = new Object[personList.size()][2];
        for(int i = 0; i < personList.size(); i++) {

            personArray[i][0] = personList.get(i);
            personArray[i][1] = exceptionList.get(i);
        }
        return personArray;
    }

    public Object[][] getUpdateNegativeData() throws IOException, URISyntaxException {

        Path filePath = Paths.get(ClassLoader.getSystemResource("UpdateNeg.csv")
                                             .toURI());

        Stream<String> lines = Files.lines(filePath);
        List<String[]> readPersonList = lines.map(persons -> persons.split(","))
                                             .collect(Collectors.toList());
        List<Person> personList = new ArrayList<>();
        List<Integer> exceptionList = new ArrayList<>();
        lines.close();

        for (String[] strings : readPersonList) {

            Address address = new Address(Integer.parseInt(strings[5]),
                                          strings[6],
                                          strings[7],
                                          Integer.parseInt(strings[8]));

            Person actualPerson = new Person(Integer.parseInt(strings[0]),
                                             strings[1],
                                             strings[2],
                                             strings[3],
                                             strings[4],
                                             address);

            int exception = Integer.parseInt(strings[9]);
            personList.add(actualPerson);
            exceptionList.add(exception);
        }

        Object[][] personArray = new Object[personList.size()][2];
        for(int i = 0; i < personList.size(); i++) {

            personArray[i][0] = personList.get(i);
            personArray[i][1] = exceptionList.get(i);
        }
        return personArray;
    }

    public Object[][] readAllData() throws IOException, URISyntaxException {

        Path filePath = Paths.get(ClassLoader.getSystemResource("readAll.csv")
                                             .toURI());
        Stream<String> lines = Files.lines(filePath);
        List<String[]> readPersonList = lines.map(persons -> persons.split(","))
                                             .collect(Collectors.toList());
        List<Person> expectedPersonList = new ArrayList<>();
        lines.close();

        for (String[] strings : readPersonList) {

            Address address = new Address(Integer.parseInt(strings[5]),
                                          strings[6],
                                          strings[7],
                                          Integer.parseInt(strings[8]));

            Person expectedPerson = new Person(Integer.parseInt(strings[0]),
                                               strings[1],
                                               strings[2],
                                               strings[3],
                                               strings[4],
                                               address);
            expectedPersonList.add(expectedPerson);
        }

        return new Object[][] {
            {expectedPersonList}
        };
    }
}
