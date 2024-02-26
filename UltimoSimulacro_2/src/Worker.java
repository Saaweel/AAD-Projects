import java.util.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.gson.annotations.SerializedName;

public class Worker {
    @SerializedName("full name")
    private String fullName;
    private String jobTitle;
    private Address address;
    private Company company;
    private String[] ips;

    public Worker(String fullName, String jobTitle, Address address, Company company, String[] ips) {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.address = address;
        this.company = company;
        this.ips = ips;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Address getaddress() {
        return address;
    }

    public void setaddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String[] getIps() {
        return ips;
    }

    public void setIps(String[] ips) {
        this.ips = ips;
    }

    @Override
    public String toString() {
        return "Worker [fullName=" + fullName + ", jobTitle=" + jobTitle + ", address=" + address + ", company=" + company
                + ", ips=" + Arrays.toString(ips) + "]";
    }

    public Node toDomNode(Document doc) {
        Element xmlWorker = doc.createElement("worker");

        Element xmlFullName = doc.createElement("fullName");
        xmlFullName.setTextContent(fullName);
        xmlWorker.appendChild(xmlFullName);

        Element xmlJobTitle = doc.createElement("jobTitle");
        xmlJobTitle.setTextContent(jobTitle);
        xmlWorker.appendChild(xmlJobTitle);

        xmlWorker.appendChild(address.toDomNode(doc));
        xmlWorker.appendChild(company.toDomNode(doc));
        
        Element xmlIps = doc.createElement("ips");
        for (String ip : ips) {
            Element xmlIp = doc.createElement("ip");
            xmlIp.setTextContent(ip);
            xmlIps.appendChild(xmlIp);
        }
        xmlWorker.appendChild(xmlIps);

        return xmlWorker;
    }
}
