/**
 * Represents a lotto draw.
 */
function Lotto() {
    /**
     * The participants
     */
    this.participants = [];

    /**
     * Add a participant.
     * @param participant The participant.
     * @param tickets The number of tickets held by the participant.
     */
    this.add = function(participant, tickets) {
        this.participants.push({ participant, tickets });
        return this;
    };

    /**
     * Draw a winning participant.
     * @returns A winning participant.
     */
    this.draw = function() {
        // We cannot do anything if there are no participants.
        if (!this.participants.length) {
            throw "cannot draw a lotto winner when there are no participants";
        }

        const pickable = [];

        this.participants.forEach(({ participant, tickets }) => {
            for (let ticketCount = 0; ticketCount < tickets; ticketCount++) {
                pickable.push(participant);
            }
        });

        return getRandomItem(pickable);
    };
}