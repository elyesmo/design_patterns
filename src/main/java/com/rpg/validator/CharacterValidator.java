package com.rpg.validator;

import com.rpg.model.Character;

/**
 * Base class for Chain of Responsibility pattern for character validation.
 * 
 * Usage:
 * <pre>
 * CharacterValidator chain = CharacterValidator.builder()
 *     .link(new NameValidator())
 *     .link(new StatsValidator())
 *     .link(new AbilitiesValidator())
 *     .build();
 * ValidationResult result = chain.validateWithErrors(character);
 * </pre>
 */
public abstract class CharacterValidator {
    protected CharacterValidator nextValidator;

    /**
     * Sets the next validator in the chain.
     * @param nextValidator The next validator
     */
    public void setNext(CharacterValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    /**
     * Fluent API to link validators.
     * @param nextValidator The next validator in the chain
     * @return the nextValidator for chaining
     */
    public CharacterValidator link(CharacterValidator nextValidator) {
        if (this.nextValidator == null) {
            this.nextValidator = nextValidator;
        } else {
            this.nextValidator.link(nextValidator);
        }
        return this;
    }

    /**
     * Simple boolean validation (legacy, stops at first error).
     * @deprecated Use validateWithErrors() for complete error reporting
     */
    @Deprecated
    public abstract boolean validate(Character character);

    /**
     * Validates the character and returns all errors aggregated.
     * @param character The character to validate
     * @return ValidationResult with all errors
     */
    public ValidationResult validateWithErrors(Character character) {
        ValidationResult currentResult = doValidate(character);
        
        if (nextValidator != null) {
            ValidationResult nextResult = nextValidator.validateWithErrors(character);
            return currentResult.combine(nextResult);
        }
        
        return currentResult;
    }

    /**
     * Performs the actual validation logic for this validator.
     * Subclasses should implement this instead of validate().
     * @param character The character to validate
     * @return ValidationResult for this validator only
     */
    protected abstract ValidationResult doValidate(Character character);

    /**
     * Calls the next validator in the chain (legacy support).
     * @deprecated Part of legacy validate() API
     */
    @Deprecated
    protected boolean callNext(Character character) {
        if (nextValidator != null) {
            return nextValidator.validate(character);
        }
        return true;
    }

    /**
     * Creates a fluent builder for the validator chain.
     * @return A new ValidatorChainBuilder
     */
    public static ValidatorChainBuilder builder() {
        return new ValidatorChainBuilder();
    }

    /**
     * Fluent builder for creating validator chains.
     */
    public static class ValidatorChainBuilder {
        private CharacterValidator first;
        private CharacterValidator last;

        public ValidatorChainBuilder link(CharacterValidator validator) {
            if (first == null) {
                first = validator;
                last = validator;
            } else {
                last.setNext(validator);
                last = validator;
            }
            return this;
        }

        public CharacterValidator build() {
            if (first == null) {
                throw new IllegalStateException("Cannot build empty validator chain");
            }
            return first;
        }
    }
}

