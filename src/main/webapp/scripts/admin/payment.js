// Get the elements for credit card option and its dropdown list
const creditCardOption = document.getElementById('creditCardOption');
const creditCardOptions = document.getElementById('creditCardOptions');

const momoOption = document.getElementById('momoOption');
const atmOption = document.getElementById('atmOption');

// Add event listener to the credit card option
creditCardOption.addEventListener('click', () => {
    // Toggle the visibility of the credit card options with sliding effect
    creditCardOptions.classList.toggle('show');
});

// Add event listeners to MoMo and ATM options to close the credit card options
momoOption.addEventListener('click', () => {
    // Hide the credit card options if MoMo is clicked
    creditCardOptions.classList.remove('show');
});

atmOption.addEventListener('click', () => {
    // Hide the credit card options if ATM is clicked
    creditCardOptions.classList.remove('show');
});

// Get all other payment options to remove the 'selected' border when clicked
const paymentOptions = document.querySelectorAll('.payment-option');

// Add click event listener to each payment option
paymentOptions.forEach(option => {
    option.addEventListener('click', () => {
        // Remove 'selected' class from all options
        paymentOptions.forEach(option => option.classList.remove('selected'));
        // Add 'selected' class to the clicked option
        option.classList.add('selected');
    });
});

// Get all payment option items in the dropdown
const paymentOptionItems = document.querySelectorAll('.payment-option-item');

// Add click event listener to each payment option item
paymentOptionItems.forEach(item => {
    item.addEventListener('click', () => {
        // Remove 'selected' class from all payment option items
        paymentOptionItems.forEach(item => item.classList.remove('selected'));
        // Add 'selected' class to the clicked item
        item.classList.add('selected');
    });
});

// Get the confirm button and add click event listener
const confirmButton = document.getElementById('confirmButton');
confirmButton.addEventListener('click', () => {
    alert('Thanh toán đã được xác nhận!');
});