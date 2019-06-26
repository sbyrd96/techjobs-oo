package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.print.attribute.standard.JobName;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("job", jobData.findById(id));
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        //validate
        if (errors.hasErrors()) {
            return "new-job";
        }

        //create new job and add it to JobData data store
        String jobName = jobForm.getName();
        Employer jobEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location jobLocation= jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency jobCoreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType jobPositionType= jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

        Job newJob = new Job(jobName, jobEmployer, jobLocation, jobPositionType, jobCoreCompetency);

        jobData.add(newJob);

        //model.addAttribute("id", newJob.getId());
        //model.addAttribute("job", newJob);
        model.addAttribute("job", newJob.getId());

        //redirect to the job detail view for the new job
        return "redirect:/job?id=" + newJob.getId();

    }
}
