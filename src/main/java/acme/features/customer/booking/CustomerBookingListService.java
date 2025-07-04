
package acme.features.customer.booking;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.Bookings.Booking;
import acme.realms.Customer;

@GuiService
public class CustomerBookingListService extends AbstractGuiService<Customer, Booking> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerBookingRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {

		boolean isCustomer = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);

		super.getResponse().setAuthorised(isCustomer);
	}

	@Override
	public void load() {
		Collection<Booking> bookings;
		int customerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		bookings = this.repository.findBookingByCustomer(customerId);

		super.getBuffer().addData(bookings);
	}

	@Override
	public void unbind(final Booking booking) {
		Dataset dataset;

		dataset = super.unbindObject(booking, "locatorCode", "purchaseMoment", "travelClass");

		super.getResponse().addData(dataset);
	}
}
