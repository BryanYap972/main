package seedu.nova.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.model.AddressBook;
import seedu.nova.model.Nova;
import seedu.nova.model.ReadOnlyAddressBook;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.person.Person;
import seedu.nova.model.util.SampleDataUtil;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "nova")
class JsonSerializableNova {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    //add your list of adapted class objects here

    /**
     * Constructs a {@code JsonSerializableNova} with the given persons.
     */
    @JsonCreator
    public JsonSerializableNova(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNova}.
     */
    public JsonSerializableNova(Nova source) {
        persons.addAll(source.getAddressBookNova().getPersonList().stream()
                .map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Javadoc
     * @return javadoc
     * @throws IllegalValueException
     */
    public Nova toModelType() throws IllegalValueException {
        Nova nova = new Nova();
        VersionedAddressBook ab = toModelTypeAb();
        //Call other toModelType();

        nova.setAddressBookNova(ab);
        //call other set methods

        return nova;
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public VersionedAddressBook toModelTypeAb() throws IllegalValueException {
        //ReadOnlyAddressBook initialState = new AddressBook();

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }


        VersionedAddressBook addressBook = new VersionedAddressBook(initialState);
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        return addressBook;
    }

    //Implement your own classes toModelType methods

}
