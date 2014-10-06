<div class="row">
    <div class="col-lg-12 text-center">
        <#if item.hasParent()>
            <span style="float:left">
                <a href="/dir/${item.getParent().getId()}">
                    <button type="button" class="btn btn-lg btn-primary">Back</button>
                </a>
            </span>
        </#if>
    </div>
</div>
